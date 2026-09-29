package br.com.spacemining.mining;
import jakarta.persistence.*;
@Entity
@Table(name="command_counts")
public class CommandCount {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(unique=true, nullable=false) private String command;
 @Column(nullable=false) private long count;
 protected CommandCount() {}
 public CommandCount(String command) { this.command=command; this.count=0; }
 public String getCommand(){return command;}
 public long getCount(){return count;}
 public void increment(){count++;}
}
