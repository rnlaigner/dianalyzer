package fi.hut.soberit.agilefant.web;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import fi.hut.soberit.agilefant.business.AuthorizationBusiness;
import fi.hut.soberit.agilefant.business.BacklogBusiness;
import fi.hut.soberit.agilefant.business.ProductBusiness;
import fi.hut.soberit.agilefant.business.TimesheetBusiness;
import fi.hut.soberit.agilefant.business.TimesheetExportBusiness;
import fi.hut.soberit.agilefant.business.UserBusiness;
import fi.hut.soberit.agilefant.model.Backlog;
import fi.hut.soberit.agilefant.model.Product;
import fi.hut.soberit.agilefant.model.User;
import fi.hut.soberit.agilefant.security.SecurityUtil;
import fi.hut.soberit.agilefant.transfer.BacklogTimesheetNode;
import flexjson.JSONSerializer;

/**
 * 
 * @author Vesa Pirila / Spider
 * @author Pasi Pekkanen
 *
 */
@Component("timesheetAction")
@Scope("prototype")
public class TimesheetAction extends ActionSupport {

    private static final long serialVersionUID = -8988740967426943267L;
    
    @Autowired
    private TimesheetBusiness timesheetBusiness;
    
    @Autowired
    private TimesheetExportBusiness timesheetExportBusiness;

    @Autowired
    private UserBusiness userBusiness;
    
    @Autowired
    private BacklogBusiness backlogBusiness;
    
    @Autowired
    private ProductBusiness productBusiness;
    
    @Autowired
    private AuthorizationBusiness authorizationBusiness;
    
    private Set<Integer> productIds = new HashSet<Integer>();
    
    private Set<Integer> projectIds = new HashSet<Integer>();
    
    private Set<Integer> iterationIds = new HashSet<Integer>();
    
    private List<BacklogTimesheetNode> products;
    
    private DateTime startDate;

    private DateTime endDate;
    
    private String timeZoneString = "";
    
    private DateTimeZone timeZone;
    
	private String interval;
    
    private Set<Integer> userIds = new HashSet<Integer>();
        
    private boolean onlyOngoing = false;
    
    private long effortSum = 0;
    
    private ByteArrayOutputStream exportableReport;
    
    private String errorMessage = "";
   
    
    public Set<Integer> getSelectedBacklogs() {
        if(this.iterationIds.size() > 0) {
            return this.iterationIds;
        } else if(this.projectIds.size() > 0) {
            return this.projectIds;
        } else if(this.productIds.size() > 0) {
            return this.productIds;
        }
        return new HashSet<Integer>();
    }
    public String initialize() {
        this.interval = "NO_INTERVAL";
        this.onlyOngoing = false;
        this.timeZone = new DateTime().getZone();
        return Action.SUCCESS;
    }
    
    private boolean checkAccess(int backlogId){
        User user = SecurityUtil.getLoggedUser();
        return this.authorizationBusiness.isBacklogAccessible(backlogId, user);
    }

    public String generateTree(){
        Set<Integer> selectedBacklogIds = this.getSelectedBacklogs();
        if(selectedBacklogIds == null || selectedBacklogIds.size() == 0) {
            Collection<Product> products = new ArrayList<Product>();
            productBusiness.storeAllTimeSheets(products);
            for (Product product: products) {
                selectedBacklogIds.add(product.getId());
            }
        }        
        if (selectedBacklogIds.contains(0))
        {
            // Standalone Iterations
            selectedBacklogIds.remove(0);
            Collection<Backlog> iters = backlogBusiness.retrieveAllStandAloneIterations();
            for (Iterator<Backlog> i = iters.iterator();i.hasNext();){
                int backlogId = i.next().getId();
                if (checkAccess(backlogId)) {
                    selectedBacklogIds.add(backlogId);
                }
            }
        }
        products = timesheetBusiness.getRootNodes(selectedBacklogIds, startDate, endDate, timeZone, this.userIds);
        effortSum = timesheetBusiness.getRootNodeSum(products);
        return Action.SUCCESS;
    }
    
    public String generateExeclReport(){
        Set<Integer> selectedBacklogIds = this.getSelectedBacklogs();
        if(selectedBacklogIds == null || selectedBacklogIds.size() == 0) {
            Collection<Product> products = new ArrayList<Product>();
            productBusiness.storeAllTimeSheets(products);
            for (Product product: products) {
                selectedBacklogIds.add(product.getId());
            }
        }        
        if (selectedBacklogIds.contains(0))
        {
            // Standalone Iterations
            selectedBacklogIds.remove(0);
            Collection<Backlog> iters = backlogBusiness.retrieveAllStandAloneIterations();
            for (Iterator<Backlog> i = iters.iterator();i.hasNext();){
                int backlogId = i.next().getId();
                if (checkAccess(backlogId)) {
                    selectedBacklogIds.add(backlogId);
                }
            }
        }
        Workbook wb = this.timesheetExportBusiness.generateTimesheet(this, selectedBacklogIds, startDate, endDate, timeZone, userIds);
        this.exportableReport = new ByteArrayOutputStream();
        try {
            wb.write(this.exportableReport);
        } catch (IOException e) {
            return Action.ERROR;
        }
        return Action.SUCCESS;
    }

    public List<User> getSelectedUsers() {
        if(this.userIds == null) {
            return Collections.emptyList();
        }
        List<User> selectedUsers = new ArrayList<User>();
        for(int userId : this.getUserIds()) {
            if (userId != UserBusiness.NON_EXISTENT_USER_ID) {
                User user = this.userBusiness.retrieve(userId);
                if(user != null) {
                    selectedUsers.add(user);
                }
            }
        }
        return selectedUsers;
    }
    public TimesheetBusiness getTimesheetBusiness() {
        return timesheetBusiness;
    }

    public void setTimesheetBusiness(TimesheetBusiness timesheetBusiness) {
        this.timesheetBusiness = timesheetBusiness;
    }

    public ProductBusiness getProductBusiness() {
		return productBusiness;
	}
	public void setProductBusiness(ProductBusiness productBusiness) {
		this.productBusiness = productBusiness;
	}

    public Set<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Integer> productIds) {
        this.productIds = productIds;
    }

    public Set<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Set<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    public Set<Integer> getIterationIds() {
        return iterationIds;
    }

    public void setIterationIds(Set<Integer> iterationIds) {
        this.iterationIds = iterationIds;
    }

    public List<BacklogTimesheetNode> getProducts() {
        return products;
    }

    public void setProducts(List<BacklogTimesheetNode> products) {
        this.products = products;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }
    
    public DateTimeZone getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(DateTimeZone timeZone) {
		this.timeZone = timeZone;
	}
	
    public String getTimeZoneString() {
		return timeZoneString;
	}
	public void setTimeZoneString(String timeZoneString) {
		this.timeZoneString = timeZoneString;
		Double timeZoneDouble = new Double(timeZoneString);
		int hoursOffset = timeZoneDouble.intValue();
		int minutesOffset = (int)Math.abs((60 * (timeZoneDouble - hoursOffset)));
		if (minutesOffset > 59 || minutesOffset < 0) {
			minutesOffset = 0;
		}
		this.timeZone = DateTimeZone.forOffsetHoursMinutes(hoursOffset, minutesOffset);
	}

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Set<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }

    public boolean isOnlyOngoing() {
        return onlyOngoing;
    }

    public void setOnlyOngoing(boolean onlyOngoing) {
        this.onlyOngoing = onlyOngoing;
    }
    
    public String getJSONProducts() {
        return new JSONSerializer().serialize(this.productIds);
    }
    public String getJSONProjects() {
        return new JSONSerializer().serialize(this.projectIds);
    }
    public String getJSONIterations() {
        return new JSONSerializer().serialize(this.iterationIds);
    }
    public long getEffortSum() {
        return effortSum;
    }
    public void setBacklogBusiness(BacklogBusiness backlogBusiness) {
        this.backlogBusiness = backlogBusiness;
    }
    public void setUserBusiness(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }
    public void setTimesheetExportBusiness(
            TimesheetExportBusiness timesheetExportBusiness) {
        this.timesheetExportBusiness = timesheetExportBusiness;
    }
    public InputStream getSheetData() {
        return new ByteArrayInputStream(this.exportableReport.toByteArray());
    }
    public void setExportableReport(ByteArrayOutputStream exportableReport) {
        this.exportableReport = exportableReport;
    }
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
