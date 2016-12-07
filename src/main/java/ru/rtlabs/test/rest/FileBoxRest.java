package ru.rtlabs.test.rest;

import ru.rtlabs.test.model.FileList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileBoxRest {
    private Properties prop = new Properties();
    private static final Logger logger = LogManager.getLogger(FileBoxRest.class);

    public FileBoxRest() {
        logger.info("Пытаемcя загрузить файл с настройками");
        try {
            InputStream in = getClass().getResourceAsStream("/prop.properties");
            prop.load(in);
            in.close();
            logger.info("Файл с настройками загружен");
            logger.info("Папка для отображения: " + prop.getProperty("DIR"));
        } catch (IOException | NullPointerException e) {
            logger.error("Файл с настройки не удалось загрузить", e);

        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public FileList getFileList() {
        logger.info("Вызван метод getFileList");
        FileList fileList = new FileList(prop.getProperty("DIR"));
        return fileList;

    }

    @GET
    @Path("/{filename}")
    public Response getFileData(@PathParam("filename") String filename) {
        logger.info("Вызван метод getFileData с параметром " + filename);
        File file = new File(prop.getProperty("DIR"), filename);
        if (file.exists() && file.isFile()) {
            logger.info("Файл найден, передаём его клиенту");
            return Response.ok()
                    .type(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=" + filename)
                    .entity(file)
                    .build();
        } else {
            logger.error("Файл " + filename + " в папке " + prop.getProperty("DIR") + " не найден");
            return Response.status(404).entity("not found").build();
        }
    }

}
