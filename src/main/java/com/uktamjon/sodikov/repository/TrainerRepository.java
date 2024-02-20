package com.uktamjon.sodikov.repository;

import com.uktamjon.sodikov.domains.Trainer;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    Trainer findByUserId(int userId);

    @Query("select t from Trainer t where t.user.isActive = ?1 and t.id not in(select f from Training f where f.trainer.id is not null)")
    List<Trainer> findAllByUserId_Active(boolean active);


}
