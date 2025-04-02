package com.example.employeedetails.service;

import com.example.employeedetails.model.Employee;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.stereotype.Service;


import java.util.concurrent.ExecutionException;

@Service
public class EmployeeService {
    private static final String COLLECTION_NAME = "employee_details";
    private final Firestore firestore;

    public EmployeeService(Firestore firestore) {
        this.firestore = firestore;
    }

    public String saveEmployeeDetails(Employee details) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection(COLLECTION_NAME).document();
        details.setid(document.getId());


        ApiFuture<WriteResult> writeResult = document.set(details);
        writeResult.get();
        return document.getId();
    }
    public Employee getEmployeeDetailsById(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = firestore.collection(COLLECTION_NAME).document(id).get().get();
        if (document.exists()) {
            return document.toObject(Employee.class);
        }
        return null;
    }
}
