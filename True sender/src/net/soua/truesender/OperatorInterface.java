package net.soua.truesender;

interface OperatorInterface {

	public boolean passwordAuth(String username, String password);
    public boolean send(String message, String source, String destination) throws Exception;
}
