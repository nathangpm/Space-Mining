package br.com.spacemining.mining;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CommandCountRepository extends JpaRepository<CommandCount,Long> {
 Optional<CommandCount> findByCommand(String command);
}
