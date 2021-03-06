package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.ContractDao;
import es.uji.ei1027.majorsacasa.model.Contract;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class ContractValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Contract.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Contract contract = (Contract) obj;
        ContractDao contractDao = new ContractDao();
        List<Contract> listaContratos = contractDao.getContracts();

        for (Contract contrato : listaContratos) {
            if (contrato.getIdNumber().equals(contract.getIdNumber()))
                errors.rejectValue("idNumber", "obligatori",
                        "Aquesta ID ja existeix");
        }

        if (contract.getIdNumber().equals("") || contract.getIdNumber().length() > 8)
            errors.rejectValue("idNumber", "obligatori",
                    "Cal introduir un ID vàlid");

        if (contract.getCif_company().equals("") || contract.getCif_company().length() != 9)
            errors.rejectValue("cif_company", "obligatori",
                    "Cal introduir un CIF vàlid");

        if (contract.getServiceType().equals(""))
            errors.rejectValue("serviceType", "obligatori",
                    "Cal introduir un valor");

        if (contract.getPrice() == 0)
            errors.rejectValue("price", "obligatori",
                    "Cal introduir un telefon de 9 cifres");

        if (contract.getDateBegining() == null)
            errors.rejectValue("dateBegining", "obligatori",
                    "Cal introduir un valor");

        if (contract.getDateEnding() == null)
            errors.rejectValue("dateEnding", "obligatori",
                    "Cal introduir un valor");

        if (!contract.getDateEnding().isAfter(contract.getDateBegining())) {
            errors.rejectValue("dateEnding", "fecha", "La data de fi te que ser posterior a la d'inici");
        }
    }
}
