package com.lkre.web.common;

import com.lkre.dao.logger.Logger;
import launch.Main;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class InfoBean {
    private final String COOKIE_MANIFEST = "We use cookies to personalise content and ads, " +
            "to provide social media features and to analyse our traffic. We also share " +
            "information about your use of our site with our social media, advertising and " +
            "analytics partners who may combine it with other information that you’ve provided to" +
            " them or that they’ve collected from your use of their services. You consent to our " +
            "cookies if you continue to use our website.";

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
