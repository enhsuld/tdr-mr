package com.peace.users.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="tbstudents")
public class User {
	private Integer id;
	private Tbuniversities tbuniversities;
	private String studentUragname;
	private String studentLastname;
	private String studentFirstname;
	private String studentRd;
	private String studentId;
	private String studentIdAddress;
	private String studentCurrentaddress;
	private String studentBornplace;
	private String studentMobilephone;
	private String studentHomephone;
	private String studentContactperson;
	private String studentNationality;
	private String studentEmail;
	private String studentEnrollId;
	private Date studentContractDate;
	private String studentContractId;
	private String studentCoursenameId;
	private int studentGpaid;
	private int studentBusId;
	private int studentCourse;
	private String StudentBusDir;
	private int universityid=0;
	private int branchid=0;
	private int departmentid=0;
	private int roleid=0;
	private String studentCardId;
	private Date studentCarddate;
	private String studentKhorootod;
	private String studentPicture;
	private String studentTomiloltId;
	private String username;
	private String password;
	private boolean enabled;
	private Set<Tbstudentbus> tbstudentbuses = new HashSet(0);
	private Set<Tbstudentgpa> tbstudentgpas = new HashSet(0);
	//private Set<PUserRoleRel> PUserRoleRels = new HashSet(0);
	private Role role;

	@Id
	@GeneratedValue
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

/*	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Student_BranchID", unique = true)
	public Tbbranches getTbbranches() {
		return this.tbbranches;
	}

	public void setTbbranches(Tbbranches tbbranches) {
		this.tbbranches = tbbranches;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Student_DepartmentID", unique = true)
	@JsonManagedReference
	public Tbdepartment getTbdepartment() {
		return this.tbdepartment;
	}

	public void setTbdepartment(Tbdepartment tbdepartment) {
		this.tbdepartment = tbdepartment;
	}*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Student_SchoolID",nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	public Tbuniversities getTbuniversities() {
		return this.tbuniversities;
	}

	public void setTbuniversities(Tbuniversities tbuniversities) {
		this.tbuniversities = tbuniversities;
	}
	
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinTable(name="user_roles",
		joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
		inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
	)
	@JsonBackReference
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
/*	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbstudents")
	public Set<PUserRoleRel> getPUserRoleRels() {
		return this.PUserRoleRels;
	}

	public void setPUserRoleRels(Set<PUserRoleRel> PUserRoleRels) {
		this.PUserRoleRels = PUserRoleRels;
	}*/
	

	@Column(name = "Student_SchoolID",  length = 45)
	public int getUniversityid() {
		return universityid;
	}

	public void setUniversityid(int universityid) {
		this.universityid = universityid;
	}


	@Column(name = "Student_BranchID",  length = 45, nullable=true)
	public int getBranchid() {
		return branchid;
	}

	public void setBranchid(int branchid) {
		this.branchid = branchid;
	}

	@Column(name = "Student_DepartmentID",  length = 45)
	public int getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}


	@Column(name = "Student_Course",  length = 10)
	public int getStudentCourse() {
		return studentCourse;
	}

	public void setStudentCourse(int studentCourse) {
		this.studentCourse = studentCourse;
	}

	@Column(name = "Student_Nationality",  length = 200)
	public String getStudentNationality() {
		return studentNationality;
	}

	public void setStudentNationality(String studentNationality) {
		this.studentNationality = studentNationality;
	}

	@Column(name = "Student_Uragname",  length = 45)
	public String getStudentUragname() {
		return this.studentUragname;
	}

	public void setStudentUragname(String studentUragname) {
		this.studentUragname = studentUragname;
	}

	@Column(name = "Student_Lastname",  length = 45)
	public String getStudentLastname() {
		return this.studentLastname;
	}

	public void setStudentLastname(String studentLastname) {
		this.studentLastname = studentLastname;
	}

	@Column(name = "Student_Firstname",  length = 45)
	public String getStudentFirstname() {
		return this.studentFirstname;
	}

	public void setStudentFirstname(String studentFirstname) {
		this.studentFirstname = studentFirstname;
	}

	@Column(name = "Student_RD",  length = 20)
	public String getStudentRd() {
		return this.studentRd;
	}

	public void setStudentRd(String studentRd) {
		this.studentRd = studentRd;
	}

	@Column(name = "Student_ID", length = 10)
	public String getStudentId() {
		return this.studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	@Column(name = "Student_bus_dir",  length = 500)
	
	public String getStudentBusDir() {
		return StudentBusDir;
	}

	public void setStudentBusDir(String studentBusDir) {
		StudentBusDir = studentBusDir;
	}
	
	@Column(name = "Student_ID_address",  length = 100)
	public String getStudentIdAddress() {
		return this.studentIdAddress;
	}

	
	

	public void setStudentIdAddress(String studentIdAddress) {
		this.studentIdAddress = studentIdAddress;
	}

	@Column(name = "Student_Currentaddress", length = 100)
	public String getStudentCurrentaddress() {
		return this.studentCurrentaddress;
	}

	public void setStudentCurrentaddress(String studentCurrentaddress) {
		this.studentCurrentaddress = studentCurrentaddress;
	}

	@Column(name = "Student_Bornplace",  length = 45)
	public String getStudentBornplace() {
		return this.studentBornplace;
	}

	public void setStudentBornplace(String studentBornplace) {
		this.studentBornplace = studentBornplace;
	}

	@Column(name = "Student_Mobilephone", length = 20)
	public String getStudentMobilephone() {
		return this.studentMobilephone;
	}

	public void setStudentMobilephone(String studentMobilephone) {
		this.studentMobilephone = studentMobilephone;
	}

	@Column(name = "Student_Homephone",  length = 20)
	public String getStudentHomephone() {
		return this.studentHomephone;
	}

	public void setStudentHomephone(String studentHomephone) {
		this.studentHomephone = studentHomephone;
	}

	@Column(name = "Student_Contactperson",  length = 20)
	public String getStudentContactperson() {
		return this.studentContactperson;
	}

	public void setStudentContactperson(String studentContactperson) {
		this.studentContactperson = studentContactperson;
	}

	@Column(name = "Student_email",  length = 45)
	public String getStudentEmail() {
		return this.studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	@Column(name = "Student_EnrollID",  length = 45)
	public String getStudentEnrollId() {
		return this.studentEnrollId;
	}

	public void setStudentEnrollId(String studentEnrollId) {
		this.studentEnrollId = studentEnrollId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Student_ContractDate", length = 10)
	public Date getStudentContractDate() {
		return this.studentContractDate;
	}

	public void setStudentContractDate(Date studentContractDate) {
		this.studentContractDate = studentContractDate;
	}

	@Column(name = "Student_ContractID", length = 45)
	public String getStudentContractId() {
		return this.studentContractId;
	}

	public void setStudentContractId(String studentContractId) {
		this.studentContractId = studentContractId;
	}

	@Column(name = "Student_CoursenameID", length = 45)
	public String getStudentCoursenameId() {
		return this.studentCoursenameId;
	}

	public void setStudentCoursenameId(String studentCoursenameId) {
		this.studentCoursenameId = studentCoursenameId;
	}

	@Column(name = "Student_GPAid")
	public int getStudentGpaid() {
		return this.studentGpaid;
	}

	public void setStudentGpaid(int studentGpaid) {
		this.studentGpaid = studentGpaid;
	}

	@Column(name = "Student_BusID")
	public int getStudentBusId() {
		return this.studentBusId;
	}

	public void setStudentBusId(int studentBusId) {
		this.studentBusId = studentBusId;
	}

	@Column(name = "Student_CardID", length = 20)
	public String getStudentCardId() {
		return this.studentCardId;
	}

	public void setStudentCardId(String studentCardId) {
		this.studentCardId = studentCardId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Student_Carddate",  length = 10)
	public Date getStudentCarddate() {
		return this.studentCarddate;
	}

	public void setStudentCarddate(Date studentCarddate) {
		this.studentCarddate = studentCarddate;
	}

	@Column(name = "Student_Khorootod", length = 45)
	public String getStudentKhorootod() {
		return this.studentKhorootod;
	}

	public void setStudentKhorootod(String studentKhorootod) {
		this.studentKhorootod = studentKhorootod;
	}

	@Column(name = "Student_Picture",  length = 100)
	public String getStudentPicture() {
		return this.studentPicture;
	}

	public void setStudentPicture(String studentPicture) {
		this.studentPicture = studentPicture;
	}

	@Column(name = "Student_TomiloltID", length = 45)
	public String getStudentTomiloltId() {
		return this.studentTomiloltId;
	}

	public void setStudentTomiloltId(String studentTomiloltId) {
		this.studentTomiloltId = studentTomiloltId;
	}

	@Column(name = "username", length = 100)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbstudents")
	public Set<Tbstudentbus>  getTbstudentbuses() {
		return this.tbstudentbuses;
	}

	public void setTbstudentbuses(Set<Tbstudentbus> tbstudentbuses) {
		this.tbstudentbuses = tbstudentbuses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbstudents")
	public Set<Tbstudentgpa> getTbstudentgpas() {
		return this.tbstudentgpas;
	}

	public void setTbstudentgpas(Set<Tbstudentgpa> tbstudentgpas) {
		this.tbstudentgpas = tbstudentgpas;
	}

	@Transient
	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

}
