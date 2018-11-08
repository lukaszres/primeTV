package com.lkre.web.common;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.lkre.dao.logger.Logger;
import launch.Main;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class InfoBean {
    @ManagedProperty(value = "#{logger}")
    private Logger logger;
    private String version;
    private String buildTime;
    private int visitors;

    @PostConstruct
    public void init() {
        buildTime = loadManifest("Build-Time");
        version = loadManifest("Implementation-Version");
        visitors = logger.count();
    }

    private String loadManifest(String manifestEntry) {
        String absolutePath = Main.getRootFolder()
                                  .getAbsolutePath();
        File file = new File(absolutePath + "/target/embeddedTomcatSample.jar");
        try {
            JarFile jar = new java.util.jar.JarFile(file);
            java.util.jar.Manifest manifest = jar.getManifest();
            Attributes mainAttributes = manifest.getMainAttributes();
            String value = mainAttributes.getValue(manifestEntry);
            jar.close();
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            return "??????";
        }
    }
}
