package com.epam.tsylko.andrei.dao.pool;


import java.util.ResourceBundle;

public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();
//    add path
        private ResourceBundle bundle = ResourceBundle.getBundle("database");

        public static DBResourceManager getInstance(){
            return instance;
        }

        public String getValue(String key){
            return bundle.getString(key);
        }
}
