package codegym.cdkteam.musichub.service;


import codegym.cdkteam.musichub.model.TokenVerifyDTO;

public interface TokenVerifyDTOService {

    TokenVerifyDTO findByUserId(long id);
    void save(TokenVerifyDTO tokenVerifyDTO);
    void delete(TokenVerifyDTO tokenVerifyDTO);
}
