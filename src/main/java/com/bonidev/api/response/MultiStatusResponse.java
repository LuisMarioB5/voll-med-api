package com.bonidev.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MultiStatusResponse {
    private List<ResourceStatus> resources = new ArrayList<>();

    public void addResource(String location, int status) {
        resources.add(new ResourceStatus(location, status));
    }

    @Getter
    @Setter
    public static class ResourceStatus {
        private String location;
        private int status;

        public ResourceStatus(String location, int status) {
            this.location = location;
            this.status = status;
        }
    }
}
