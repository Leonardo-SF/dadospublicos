package br.com.dadospublicos.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TesteService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void print(Object... objects) {
        Arrays.stream(objects).forEach(object -> logger.info(object.toString()));
    }

}
