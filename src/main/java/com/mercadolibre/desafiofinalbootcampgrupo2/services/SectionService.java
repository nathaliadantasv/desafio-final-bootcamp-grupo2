package com.mercadolibre.desafiofinalbootcampgrupo2.services;

import com.mercadolibre.desafiofinalbootcampgrupo2.dao.SectionDAO;
import com.mercadolibre.desafiofinalbootcampgrupo2.exception.RepositoryException;
import com.mercadolibre.desafiofinalbootcampgrupo2.model.Batch;
import com.mercadolibre.desafiofinalbootcampgrupo2.model.Section;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService implements EntityService<Section> {

    private SectionDAO sectionDAO;

    public SectionService(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
    }

    @Override
    public Section findById(Long id) {
        return sectionDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Section not exists in the Database, please contact the administrator"));
    }
  
   public void calVolumeCheckin(Batch batch, Section section){

        section.calVolumeCheckin(batch);
        sectionDAO.save(section);
    }

    public void calVolumeCheckout(Batch batch, Section section){

        section.calVolumeCheckout(batch);
        sectionDAO.save(section);
    }

    public void verifyIfSectionHaveSpaceEnoughToAddBatches(Section section, List<Batch> batchs) {
        for (Batch batch : batchs) {
            if (section.calVolumeCheckin(batch) < 0) {
                throw new RepositoryException("Space not available in the section, please contact an administrator");
            }
        }
    }
}