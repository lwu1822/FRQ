package com.nighthawk.team_backend.mvc.database.note;

import java.util.List;

import javax.transaction.Transactional;

import com.nighthawk.team_backend.mvc.database.cipher.Cipher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteJpaRepository extends JpaRepository<Note, Long> {
    List<Cipher> findByCipherId(Long id);

    @Transactional
    void deleteByCipherId(long id);

    static List<Note> findAllByCipher(Cipher cipher) {
        return null;
    }
}
