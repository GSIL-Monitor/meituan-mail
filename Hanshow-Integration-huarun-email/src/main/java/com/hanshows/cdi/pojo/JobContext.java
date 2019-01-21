package com.hanshows.cdi.pojo;

import com.hanshows.cdi.generic.json.JsonAPI;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class JobContext {
	private ApplicationContext springContext;
	private PropertiesConfiguration configuration;
	private Map<String, String> enabledStores;
	private File localDataSource;


	private DataSource remoteDataSourceMysql;
	private DataSource remoteDataSourceOracle;
	private JsonAPI jsonAPI;
	private File tmpDir;
	private Locale locale;
	private TimeZone timeZone;
	private String charset;
	private String ftpServerHost;
	private int ftpServerPort;
	private String ftpUsername;
	private String ftpPassword;
	private String ftpPath;
	private int integrationDelay;

	private File preparefilePath;
	private File backfilePath;
	private File exceptionfilePath;
	private File executefilePath;
	private File preparefileMd5Path;

	private String localPath;




	private int alarmLimit;
	private String anonymous;
	private String mailhost;
	private String mailcharset;
	private String[] mailaddressee;
	private String sender;
	private String usr;
	private String pwd;
	private String alarm;
	
	
	private int shalarmLimit;
	private String shanonymous;
	private String shmailhost;
	private String shmailcharset;
	private String[] shmailaddressee;
	private String shsender;
	private String shusr;
	private String shpwd;
	private String shalarm;
	
	private List emailField;
	private List status;
	private List eslPrField;
	
	private String storeCode;
	
	private int ftpswitch;
	private int mailswitch;
	private int localswitch;
	
	private File getFileEmailPath;
	
	
	
	public File getGetFileEmailPath() {
		return getFileEmailPath;
	}

	public void setGetFileEmailPath(File getFileEmailPath) {
		this.getFileEmailPath = getFileEmailPath;
	}

	public List getEslPrField() {
		return eslPrField;
	}

	public void setEslPrField(List eslPrField) {
		this.eslPrField = eslPrField;
	}

	public int getFtpswitch() {
		return ftpswitch;
	}

	public void setFtpswitch(int ftpswitch) {
		this.ftpswitch = ftpswitch;
	}

	public int getMailswitch() {
		return mailswitch;
	}

	public void setMailswitch(int mailswitch) {
		this.mailswitch = mailswitch;
	}

	public int getLocalswitch() {
		return localswitch;
	}

	public void setLocalswitch(int localswitch) {
		this.localswitch = localswitch;
	}

	public String getFtpPath() {
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	public File getLocalDataSource() {
		return localDataSource;
	}

	public void setLocalDataSource(File localDataSource) {
		this.localDataSource = localDataSource;
	}
	
	 public List getStatus() {
		return status;
	}

	public void setStatus(List status) {
		this.status = status;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public List getEmailField() {
		return emailField;
	}

	public void setEmailField(List emailField) {
		this.emailField = emailField;
	}

	public int getShalarmLimit() {
		return shalarmLimit;
	}

	public void setShalarmLimit(int shalarmLimit) {
		this.shalarmLimit = shalarmLimit;
	}

	public String getShanonymous() {
		return shanonymous;
	}

	public void setShanonymous(String shanonymous) {
		this.shanonymous = shanonymous;
	}

	public String getShmailhost() {
		return shmailhost;
	}

	public void setShmailhost(String shmailhost) {
		this.shmailhost = shmailhost;
	}

	public String getShmailcharset() {
		return shmailcharset;
	}

	public void setShmailcharset(String shmailcharset) {
		this.shmailcharset = shmailcharset;
	}

	public String[] getShmailaddressee() {
		return shmailaddressee;
	}

	public void setShmailaddressee(String[] shmailaddressee) {
		this.shmailaddressee = shmailaddressee;
	}

	public String getShsender() {
		return shsender;
	}

	public void setShsender(String shsender) {
		this.shsender = shsender;
	}

	public String getShusr() {
		return shusr;
	}

	public void setShusr(String shusr) {
		this.shusr = shusr;
	}

	public String getShpwd() {
		return shpwd;
	}

	public void setShpwd(String shpwd) {
		this.shpwd = shpwd;
	}

	public String getShalarm() {
		return shalarm;
	}

	public void setShalarm(String shalarm) {
		this.shalarm = shalarm;
	}

	private File contentDir;

	public File getContentDir() {
		return contentDir;
	}

	public void setContentDir(File contentDir) {
		this.contentDir = contentDir;
	}

	public int getAlarmLimit() {
		return alarmLimit;
	}

	public void setAlarmLimit(int alarmLimit) {
		this.alarmLimit = alarmLimit;
	}

	public String getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}

	public String getMailhost() {
		return mailhost;
	}

	public void setMailhost(String mailhost) {
		this.mailhost = mailhost;
	}

	public String getMailcharset() {
		return mailcharset;
	}

	public void setMailcharset(String mailcharset) {
		this.mailcharset = mailcharset;
	}

	public String[] getMailaddressee() {
		return mailaddressee;
	}

	public void setMailaddressee(String[] mailaddressee) {
		this.mailaddressee = mailaddressee;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public File getPreparefilePath() {
		return preparefilePath;
	}

	public void setPreparefilePath(File preparefilePath) {
		this.preparefilePath = preparefilePath;
	}

	public File getBackfilePath() {
		return backfilePath;
	}

	public void setBackfilePath(File backfilePath) {
		this.backfilePath = backfilePath;
	}

	public File getExceptionfilePath() {
		return exceptionfilePath;
	}

	public void setExceptionfilePath(File exceptionfilePath) {
		this.exceptionfilePath = exceptionfilePath;
	}

	public File getExecutefilePath() {
		return executefilePath;
	}

	public void setExecutefilePath(File executefilePath) {
		this.executefilePath = executefilePath;
	}

	public File getPreparefileMd5Path() {
		return preparefileMd5Path;
	}

	public void setPreparefileMd5Path(File preparefileMd5Path) {
		this.preparefileMd5Path = preparefileMd5Path;
	}

	public Set<String> getAllStores() {
		return enabledStores.keySet();
	}



	public DataSource getRemoteDataSourceMysql() {
		return remoteDataSourceMysql;
	}

	public void setRemoteDataSourceMysql(DataSource remoteDataSourceMysql) {
		this.remoteDataSourceMysql = remoteDataSourceMysql;
	}

	public DataSource getRemoteDataSourceOracle() {
		return remoteDataSourceOracle;
	}

	public void setRemoteDataSourceOracle(DataSource remoteDataSourceOracle) {
		this.remoteDataSourceOracle = remoteDataSourceOracle;
	}

	public JsonAPI getJsonAPI() {
		return jsonAPI;
	}

	public void setJsonAPI(JsonAPI jsonAPI) {
		this.jsonAPI = jsonAPI;
	}

	public PropertiesConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(PropertiesConfiguration configuration) {
		this.configuration = configuration;
	}

	public Map<String, String> getEnabledStores() {
		return enabledStores;
	}

	public void setEnabledStores(Map<String, String> enabledStores) {
		this.enabledStores = enabledStores;
	}

	public File getTmpDir() {
		return tmpDir;
	}

	public void setTmpDir(File tmpDir) {
		this.tmpDir = tmpDir;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getFtpServerHost() {
		return ftpServerHost;
	}

	public void setFtpServerHost(String ftpServerHost) {
		this.ftpServerHost = ftpServerHost;
	}

	public int getFtpServerPort() {
		return ftpServerPort;
	}

	public void setFtpServerPort(int ftpServerPort) {
		this.ftpServerPort = ftpServerPort;
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public ApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(ApplicationContext springContext) {
		this.springContext = springContext;
	}

	public int getIntegrationDelay() {
		return integrationDelay;
	}

	public void setIntegrationDelay(int integrationDelay) {
		this.integrationDelay = integrationDelay;
	}

}
