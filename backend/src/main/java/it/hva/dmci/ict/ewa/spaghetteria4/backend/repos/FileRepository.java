package it.hva.dmci.ict.ewa.spaghetteria4.backend.repos;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.File;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByName(String name);

    Optional<File> findById(Long Id);
}
