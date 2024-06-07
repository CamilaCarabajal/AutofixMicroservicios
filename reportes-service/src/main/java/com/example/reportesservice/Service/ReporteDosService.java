package com.example.reportesservice.Service;

import com.example.reportesservice.Repository.ReporteDosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class ReporteDosService {
    @Autowired
    ReporteDosRepository reporteDosRepository;
    @Autowired
    private RestTemplate restTemplate;


}
