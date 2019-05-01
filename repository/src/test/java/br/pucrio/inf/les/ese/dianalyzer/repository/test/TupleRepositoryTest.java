package br.pucrio.inf.les.ese.dianalyzer.repository.test;

import java.util.List;

import br.pucrio.inf.les.ese.dianalyzer.repository.configuration.DataSourceConfig;
import br.pucrio.inf.les.ese.dianalyzer.repository.configuration.JpaConfig;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.Tuple;
import br.pucrio.inf.les.ese.dianalyzer.repository.repository.TupleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;


@TestPropertySource(locations = "classpath:hibernate.properties")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DataSourceConfig.class, JpaConfig.class })
public class TupleRepositoryTest {

    @Autowired
    private TupleRepository tupleRepository;

    @Test
    public void repositoryWorksCCPD() throws Exception {

        Long count = tupleRepository.count();

        System.out.println("Count: "+count);

        List<Tuple> list = (List<Tuple>) tupleRepository.findAll();

        for (Tuple entity : list) {
            System.out.println(entity);
        }

        assertTrue( count == 0 );

    }



}
