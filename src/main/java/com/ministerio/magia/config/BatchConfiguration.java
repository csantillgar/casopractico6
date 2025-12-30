package com.ministerio.magia.config;

import com.ministerio.magia.model.Hechizo;
import com.ministerio.magia.batch.HechizoItemProcessor;
import com.ministerio.magia.listener.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing // Habilita la infraestructura de procesamiento por lotes [cite: 262]
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Hechizo> reader() {
        return new FlatFileItemReaderBuilder<Hechizo>()
                .name("hechizoReader")
                .resource(new ClassPathResource("hechizos.csv"))
                .delimited()
                .names(new String[]{"id", "nombre", "tipo", "nivel"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Hechizo>() {{ setTargetType(Hechizo.class); }})
                .build();
    }

    @Bean
    public HechizoItemProcessor processor() {
        return new HechizoItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Hechizo> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Hechizo>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO hechizo (id, nombre, tipo, nivel) VALUES (:id, :nombre, :tipo, :nivel)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Hechizo> writer) {
        return stepBuilderFactory.get("step1")
                .<Hechizo, Hechizo>chunk(10) // Procesamiento por lotes para eficiencia [cite: 125, 392]
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public Job importHechizoJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importHechizoJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener) // Monitorización mediante listeners [cite: 174, 383]
                .flow(step1)
                .end()
                .build();
    }
}