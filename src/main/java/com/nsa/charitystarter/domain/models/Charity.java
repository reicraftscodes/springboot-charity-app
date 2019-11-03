//package com.nsa.charitystarter.domain.models;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//@Data
//@AllArgsConstructor
//public class Charity {
//    private Long id;
//
//    private String name;
//    private String registrationNumber; //known as the number, but in Scotland it starts with an 'S'
//    private String acronym; //useful for searches
//    private String tags; //comma-separated keywords (again for searching)
//    //private String missionStatement;
//    //private String imagePath;
//    //private Boolean approved;
//
//    public String searchable() {
//        return String.join(";", name, registrationNumber,
//                acronym, tags);
//    }
//}
