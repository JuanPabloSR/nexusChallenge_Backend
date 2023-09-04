package com.nexus.inventory.service.merchandise;

import com.nexus.inventory.dtos.merchandise.MerchandiseDTO;
import com.nexus.inventory.exceptions.RequestException;
import com.nexus.inventory.model.merchandise.Merchandise;
import com.nexus.inventory.model.user.User;
import com.nexus.inventory.repository.merchandise.MerchandiseRepository;
import com.nexus.inventory.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MerchandiseServiceImpl implements MerchandiseService {

    private final MerchandiseRepository merchandiseRepository;
    private final UserService userService;

    public MerchandiseServiceImpl(MerchandiseRepository merchandiseRepository, UserService userService) {
        this.merchandiseRepository = merchandiseRepository;
        this.userService = userService;
    }


    @Override
    public Merchandise saveMerchandise(MerchandiseDTO merchandiseDTO) {
        validateMerchandiseDoesNotExist(merchandiseDTO.getProductName());

        User registeredBy = getUserOrThrow(merchandiseDTO.getRegisteredById());
        LocalDate entryDate = getValidEntryDate(merchandiseDTO.getEntryDate());

        Merchandise merchandise = createMerchandiseFromDTO(merchandiseDTO, registeredBy, entryDate);
        return merchandiseRepository.save(merchandise);
    }

    public void validateMerchandiseDoesNotExist(String productName) {
        if (merchandiseRepository.existsByProductName(productName)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "The merchandise " + productName + " already exists");
        }
    }

    private User getUserOrThrow(Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new RequestException(HttpStatus.NOT_FOUND, "The user with ID " + userId + " does not exist");
        }
        return user;
    }

    private LocalDate getValidEntryDate(LocalDate entryDate) {
        LocalDate currentDate = LocalDate.now();
        if (entryDate == null || entryDate.isAfter(currentDate)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "Invalid entryDate");
        }
        return entryDate;
    }

    private Merchandise createMerchandiseFromDTO(MerchandiseDTO merchandiseDTO, User registeredBy, LocalDate entryDate) {
        Merchandise merchandise = new Merchandise();
        merchandise.setProductName(merchandiseDTO.getProductName());
        merchandise.setQuantity(merchandiseDTO.getQuantity());
        merchandise.setEntryDate(entryDate);
        merchandise.setRegisteredBy(registeredBy);
        return merchandise;
    }

    @Override
    public Merchandise updateMerchandise(Long merchandiseId, MerchandiseDTO updateMerchandiseDto) {
        Merchandise merchandiseToUpdate = getMerchandiseOrThrow(merchandiseId);
        User editedBy = getUserOrThrow(updateMerchandiseDto.getEditedById());
        LocalDate entryDate = getValidEntryDate(updateMerchandiseDto.getEntryDate());

        updateMerchandiseFromDTO(merchandiseToUpdate, updateMerchandiseDto, editedBy, entryDate);
        return merchandiseRepository.save(merchandiseToUpdate);
    }

    private Merchandise getMerchandiseOrThrow(Long merchandiseId) {
        return merchandiseRepository.findById(merchandiseId)
                .orElseThrow(() -> new RequestException(HttpStatus.NOT_FOUND, "The merchandise you want to edit does not exist"));
    }

    private void updateMerchandiseFromDTO(Merchandise merchandiseToUpdate, MerchandiseDTO updateMerchandiseDTO, User editedBy, LocalDate entryDate) {
        merchandiseToUpdate.setProductName(updateMerchandiseDTO.getProductName());
        merchandiseToUpdate.setQuantity(updateMerchandiseDTO.getQuantity());
        merchandiseToUpdate.setEntryDate(entryDate);
        merchandiseToUpdate.setEditedBy(editedBy);
        merchandiseToUpdate.setEditDate(LocalDate.now());
    }

    @Override
    public Merchandise findById(Long merchandiseId) {
        return merchandiseRepository.findById(merchandiseId)
                .orElseThrow(() ->new  RequestException(HttpStatus.NOT_FOUND, "Merchandise by id:" + merchandiseId + " not found"));
    }


    @Override
    public List<Merchandise> findAllMerchandise() {
        List<Merchandise> merchandiseList = merchandiseRepository.findAll();
        if (merchandiseList.isEmpty()) {
            throw new RequestException(HttpStatus.NOT_FOUND, "No merchandise exists");
        }
        return merchandiseList;
    }

    @Override
    public void deleteMerchandise(Long merchandiseId) {
        if (!merchandiseRepository.existsById(merchandiseId)) {
            throw new RequestException(HttpStatus.NOT_FOUND, "The Merchandise you wish to delete does not exist");
        }
        merchandiseRepository.deleteById(merchandiseId);
    }
}
