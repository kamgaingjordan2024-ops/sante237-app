package com.sante237.backend.service;
import com.sante237.backend.dto.SpecialiteRequestDTO;
import com.sante237.backend.dto.SpecialiteResponseDTO;
import java.util.List;

public interface ISpecialiteService {
    List<SpecialiteResponseDTO> getAllSpecialites();
    SpecialiteResponseDTO getSpecialiteById(Long id);
    SpecialiteResponseDTO createSpecialite(SpecialiteRequestDTO dto);
    SpecialiteResponseDTO updateSpecialite(Long id, SpecialiteRequestDTO dto);
    boolean deleteSpecialite(Long id);
    boolean specialiteExists(Long id);
}