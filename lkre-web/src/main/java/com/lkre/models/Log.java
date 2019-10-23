package com.lkre.models;

import com.lkre.dao.logger.Activity;
import com.lkre.dao.logger.Site;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Log {
    int id;
    Date created;
    Site site;
    String ip;
    Activity activity;
    String details;
    String userAgent;
}
