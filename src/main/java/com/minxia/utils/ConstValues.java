package com.minxia.utils;

public interface ConstValues {
	public final String Version ="2014-03-31";
	public final String hostReqMessageID ="hostReqMessageID";
	public final String hostName ="PHT018D";
	public final String loggingKey ="loggingKey";
	public final String DESTINATION="DESTINATION";
	public final String IMSTRANSACTIONNAME = "IMSTRANSACTIONNAME";
	
	public final String REQUEST_MAPPING_DIR="req.mapping.dir";
	public final String RC_REQUEST_TEMPLATE_DIR="req.template.dir";
	public final String TAF_MAPPING_DIR="resp.mapping.dir";
	public final String TAF_RESPONSE_TEMPLATE_DIR="resp.template.dir";
	public final String LOG_DIR="log.dir";
	public final String TABLE_DIR="table.dir";
	public final String TAFI_CONFIG ="tafi.config";
	
	public final String REQ_MAPPING_PRE="REQ_Mapping_Pre";
	public final String TAF_MAPPING_PRE="TAF_Mapping_Pre";
	public final String RC_REQUEST_PRE="RC_Request_Pre";
	public final String TAF_RESPONSE_PRE="TAF_Response_Pre";
	public final String TAFIDIR="TAFIDIR";
	
	public final String TAG_FIELD = "field";
	public final String TAG_LIST = "list";
	public final String ATR_VALUE = "value";
	public final String ATR_NAME = "name";
	public final String ATR_PATH = "path";
	public final String ATR_TRX = "trx";
	
	public final String TRX = "trx";
	public final String IMS = "ims";
	public final String REGION = "region";
	public final String CKTID = "cktid";
	public final String IS_SUBS = "is_subs";
	
	public final String MDC_msgId = "msgId";  //For log4j MDC
	
	public final String MAPPPING_FIlI_EXT = ".mp";
	public final String TEMPLATE_FIlI_EXT = ".vm";
	
	public final String CSP_COOKIE_attESSec = "attESSec";
	public final String CSP_COOKIE_attESHr = "attESHr";
	public final String CKTID_1 = "CKTID_1";
	
	public final String REQQ_MSG_SELECTOR = "ReqQ.messageSelector";
	
	public interface BeanName{
		public final String ImsTrxNameBean ="ImsTrxNameBean";
		public final String JMSHeader ="JMSHeader";
		public final String fackClient ="fackClient"; 
		public final String cacheClient ="cacheClient";
		public final String hostInfo ="hostInfo"; 
		public final String serverClients ="serverClients";  
		public final String trxMapping ="trxMapping"; 
		public final String LOMS_TRX_Mapping ="LOMS_TRX_Mapping"; 
		public final String dBaseAccess ="dBaseAccess";
		public final String infoMixDB ="infoMixDb";
		public final String oracleTemplate ="oracleTemplate";
		public final String nowSender = "nowSender";
		public final String timeOutManager = "timeOutManager";
		public final String LocalQueue = "localQueue";
		public final String REQUEST_FILTERS = "requestFilters"; 
		public final String REQ_EXECUTOR = "reqExecutor";
		public final String RESPONSE_FILTERS = "responseFilters"; 
		public final String HOSTREQ_POOL = "hostReq_Pool"; 
		
	}
	public interface TrxName{
		public final String TR_L = "TR";  //L means LOMS trx name.
		public final String TRUPD_L = "TRUPD";  //L means LOMS trx name.
		public final String ETRUPD_L = "ETRUPD";  //L means LOMS trx name.
		public final String NOW_L = "DLETH";
		public final String NOW = "NOW";
		public final String RPTADD = "RPTADD";
		public final String TREUPD = "TREUPD";
		public final String OSSTR = "OSSTR";
		public final String SEARCH = "SEARCH";
		public final String LNP = "LNP";
		public final String WHP = "WHP";
		public final String ERDS = "ERDS";
		public final String RETRIEVEDETAILS = "RETRIEVEDETAILS";
		public final String ISSUECREATION = "EMS_ISSUECREATION";
		public final String AMNQ = "AMNQ";
		public final String FTTX = "FTTX";
	}
	
	public interface ErrCode {
		final int ERR_FALSE = 0;
		final int ERR_TRUE = 1;
		final int ERR_UNKNOW_REQTYPE = 301;
		final int ERR_REQ_FAIL = 302;
		final int ERR_TIMEOUT = 303;
		final int ERR_RESP_FAIL = 304;
		final int ERR_MWS_DONW = 305;  //TODO: How to identify it?
		
		final int ERR_CSP_AUTH_ERROR = 320;
		final int ERR_CSP_MISSING_USER_OR_PWD = 321;
		
		final int ERR_SEARCH_FAIL_FOR_DPA = 311;  
	}
}
