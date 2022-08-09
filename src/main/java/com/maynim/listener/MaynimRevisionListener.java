package com.maynim.listener;

import com.maynim.entity.Revision;
import org.hibernate.envers.RevisionListener;

public class MaynimRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        ((Revision) revisionEntity).setUsername("maynim");
    }
}
