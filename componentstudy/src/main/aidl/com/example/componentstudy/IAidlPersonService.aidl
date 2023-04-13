// IAidlServiceInterface.aidl
package com.example.componentstudy;

// Declare any non-default types here with import statements

import com.example.componentstudy.pojo.Person;

interface IAidlPersonService {
    boolean addPerson(in Person person);
    List<Person> getPersonList();
}