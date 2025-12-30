package com.ministerio.magia.batch;

import com.ministerio.magia.model.Hechizo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class HechizoItemProcessor implements ItemProcessor<Hechizo, Hechizo> {

    private static final Logger log = LoggerFactory.getLogger(HechizoItemProcessor.class);

    @Override
    public Hechizo process(final Hechizo hechizo) throws Exception {
        final String nombreTransformado = hechizo.getNombre().toUpperCase();

        final Hechizo transformado = new Hechizo(
                hechizo.getId(),
                nombreTransformado,
                hechizo.getTipo(),
                hechizo.getNivel()
        );

        log.info("Transformando (" + hechizo + ") a (" + transformado + ")");
        return transformado;
    }
}