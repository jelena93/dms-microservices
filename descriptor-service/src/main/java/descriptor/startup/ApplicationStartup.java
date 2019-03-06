package descriptor.startup;

import descriptor.domain.Descriptor;
import descriptor.domain.DescriptorType;
import descriptor.domain.DocumentType;
import descriptor.service.DescriptorTypeService;
import descriptor.service.DocumentTypeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ApplicationStartup implements InitializingBean {

    private final DocumentTypeService documentTypeService;
    private final DescriptorTypeService descriptorTypeService;

    @Autowired
    public ApplicationStartup(DocumentTypeService documentTypeService, DescriptorTypeService descriptorTypeService) {
        this.documentTypeService = documentTypeService;
        this.descriptorTypeService = descriptorTypeService;
    }

    @Override
    public void afterPropertiesSet() {
        DescriptorType descriptorTypeInteger = new DescriptorType(1, Integer.class);
        DescriptorType descriptorTypeDouble = new DescriptorType(2, Double.class);
        DescriptorType descriptorTypeDate = new DescriptorType(3, Date.class);

        descriptorTypeInteger = descriptorTypeService.save(descriptorTypeInteger);
        descriptorTypeDouble = descriptorTypeService.save(descriptorTypeDouble);
        descriptorTypeDate = descriptorTypeService.save(descriptorTypeDate);

        DocumentType documentType = new DocumentType(1, "Nalog za placanje");
        Descriptor descriptor = new Descriptor(1, "Broj naloga", documentType, descriptorTypeInteger);
        documentType.getDescriptors().add(descriptor);
        descriptor = new Descriptor(2, "Suma", documentType, descriptorTypeDouble);
        documentType.getDescriptors().add(descriptor);
        descriptor = new Descriptor(3, "Datum", documentType, descriptorTypeDate);
        documentType.getDescriptors().add(descriptor);
        documentTypeService.save(documentType);

        documentType = new DocumentType(2, "Profaktura dobavljaca");
        descriptor = new Descriptor(4, "Broj profakture", documentType, descriptorTypeInteger);
        documentType.getDescriptors().add(descriptor);
        descriptor = new Descriptor(5, "Datum", documentType, descriptorTypeDate);
        documentType.getDescriptors().add(descriptor);
        descriptor = new Descriptor(6, "Suma", documentType, descriptorTypeDouble);
        documentType.getDescriptors().add(descriptor);
        documentTypeService.save(documentType);
    }
}
