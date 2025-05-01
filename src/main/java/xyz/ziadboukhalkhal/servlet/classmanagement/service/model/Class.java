package xyz.ziadboukhalkhal.servlet.classmanagement.service.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Class {
    private Long id;
    private String name;
    private String description;
    private String schedule;
    private String teacher;
    private String room;
}
