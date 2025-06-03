package org.example.service;

public class ServiceManager {
    public final AuthenticationService authenticationService;
    public final AdminService adminService;
    public final AgentService agentService;
    public final PropertyService propertyService;
    public final ClientService clientService;

    public ServiceManager(AuthenticationService auth, AdminService admin, AgentService agent, PropertyService property, ClientService client) {
        this.authenticationService = auth;
        this.adminService = admin;
        this.agentService = agent;
        this.propertyService = property;
        this.clientService = client;
    }


}