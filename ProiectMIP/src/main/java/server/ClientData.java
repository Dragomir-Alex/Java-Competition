package server;

import database.model.PersoanaEntity;

public class ClientData {
    public enum ClientStatus { NONE, LOGGED_USER, LOGGED_ADMIN }
    public ClientStatus clientStatus;
    public PersoanaEntity clientEntity;

    public ClientData() {
        clientStatus = ClientStatus.NONE;
        clientEntity = null;
    }
}
