package com.acme.catchup.platform.news.domain.model.aggregates;

import com.acme.catchup.platform.news.domain.model.commands.CreateFavoriteSourceCommand;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)//para q se usen recursos d eauditoria
public class FavoriteSource extends AbstractAggregateRoot<FavoriteSource> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;//aggregate siempre tiene id

    @Column(nullable = false)
    @Getter
    private String newsApiKey;

    @Column(nullable = false)
    @Getter
    private String sourceId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;


    //Bajo DDD un aggregate se crea a partir de un command, spinrg JPA necesits aun constructor por defecto

    public FavoriteSource(CreateFavoriteSourceCommand command){
        this.newsApiKey = command.newsApiKey();
        this.sourceId = command.sourceId();
        registerEvent(new FavoriteSourceCreatedEvent(this));
    }

}
