package com.nighthawk.team_backend.mvc.database.note;

import java.util.List;

import javax.transaction.Transactional;

import com.nighthawk.team_backend.mvc.database.club.Club;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteJpaRepository extends JpaRepository<Note, Long> {
    List<Club> findByClubId(Long id);

    @Transactional
    void deleteByClubId(long id);

    static List<Note> findAllByClub(Club club) {
        return null;
    }
}
