package com.exalt.reddit.model;

import com.exalt.reddit.service.BeanUtil;
import lombok.*;
import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Auditable {

    @Id @GeneratedValue private Long id;

    @NonNull private String body;

    @ManyToOne private Link link;

    public Comment(@NonNull String body, Link link) {
        this.body = body;
        this.link = link;
    }

    public String getPrettyTime() {
        PrettyTime prettyTime = BeanUtil.getBean(PrettyTime.class);

        return prettyTime.format(convertToDateViaInstant(getCreatedDate()));
    }

    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
}
