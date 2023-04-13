// IAidlServiceInterface.aidl
package com.example.appservice;

// Declare any non-default types here with import statements

import com.example.appservice.Person;

interface IAidlServiceInterface {
    boolean addPerson(in Person person);
    List<Person> getPersonList();
}