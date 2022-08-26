package com.ntuc.jpa;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class JpaController {
	
	 private static final DecimalFormat df = new DecimalFormat("0.00");

	BCryptPasswordEncoder bcEncoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository repo;

	@Autowired
	private RoleRepository rolerepo;

	@Autowired
	private CustomerRepository cr;

	// Start of Customer Details
	@RequestMapping("/customers")
	public String showCust(Model m) {
		m.addAttribute("custlist", cr.findAll());
		return "customers";
	}

	@RequestMapping("/customeraccounts")
	public String showCustAcc(Model m) {
		m.addAttribute("custlist", cr.findAll());
		return "customer_accounts";
	}

	@RequestMapping("/showadd")
	public String showadd() {
		return "add";
	}

	@RequestMapping("/add")
	public String addCust(@RequestParam(required = false) Integer cid, @RequestParam String cname,
			@RequestParam String loc, @RequestParam Double balance, RedirectAttributes ra) {
		Customers cs = new Customers(cid, cname, loc, balance);
		cr.save(cs);
		if (cid != null) {
			ra.addFlashAttribute("message", "Account ID: " + cid + " for " + cname + " has been updated.");
		} else {
			ra.addFlashAttribute("message", "Account has been created for " + cname + " with balance of $ " + df.format(balance));
		}
		return "redirect:/customers";
	}

	@RequestMapping("/getOneCust")
	@ResponseBody
	public Optional<Customers> showOne(Integer id) {
		System.out.print(id);
		return cr.findById(id);
	}

	@RequestMapping("/searchOneCustAcc")
	public String searchOneAcc(@RequestParam Integer sid, Model m) {
		List<Customers> custlist = new ArrayList<Customers>();
		custlist.add(cr.findById(sid).get());
		m.addAttribute("custlist", custlist);
		return "customer_accounts";
	}

	@RequestMapping("/searchOneCust")
	public String searchOneCust(@RequestParam Integer sid, Model m) {
		List<Customers> custlist = new ArrayList<Customers>();
		custlist.add(cr.findById(sid).get());
		m.addAttribute("custlist", custlist);
		return "customers";
	}


	@RequestMapping("/delete")
	public String delCust(@RequestParam Integer cidd, @RequestParam String cnamed, RedirectAttributes ra) {
		cr.deleteById(cidd);
		ra.addFlashAttribute("message", "Account ID: " + cidd + " for " + cnamed + " has been deleted.");
		return "redirect:/customers";
	}

	// End of Customer Details

	// Start of User Details

	@RequestMapping("/users")
	public String ShowUsersList(Model model) {
		java.util.List<Users> listUsers = repo.findAll();
		// System.out.println(listUsers.get(0).getPassword());
		model.addAttribute("listUsers", listUsers);

		return "users";
	}

	@GetMapping("/users/new")
	public String ShowUserform(Model model) {

		List<UserRole> roles = rolerepo.findAll();
		model.addAttribute("roles", roles);
		model.addAttribute("Users", new Users());
		return "user_form";
	}

	// hardcoded creation of user with input String Role as argument

//	@GetMapping("/users/test")
//	public String ShowUsertest(Model model) {
//		UserRole ur = new UserRole();
//		List<UserRole> roles = rolerepo.findAll();
//		for (int i = 0; i < roles.size(); i++) {
//			if (roles.get(i).getName().equals("ADMIN")) {
//				ur.setId(roles.get(i).getId());
//			}
//		}
//		ur.setName("ADMIN");
//p
//		Set<UserRole> uset = new HashSet<>();
//		uset.add(ur);
//		Users user = new Users((long) 10, "Name", "photo", true, "photo", uset);
//		repo.save(user);
//		return "index";
//	}

	@PostMapping("/users/save")
	public RedirectView saveUser(Users user, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		user.setPhotos(fileName);
		String epass = bcEncoder.encode(user.getPassword());
		user.setPassword(epass);
		Users savedUser = repo.save(user);

		String uploadDir = "user-photos/" + savedUser.getId();

		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		return new RedirectView("/users", true);
	}

	@GetMapping("/users/edit/{id}")
	public String ShowEditForm(@PathVariable("id") Long id, Model model) {
		Users user = repo.findById(id).get();
		model.addAttribute("Users", user);

		List<UserRole> roles = rolerepo.findAll();
		model.addAttribute("roles", roles);

		return "user_form";
	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model) {
		repo.deleteById(id);

		return "redirect:/users";

	}

	// End of User Details

	// Start of Withdraw_Deposit + transfer Details
	@RequestMapping("/deposit")
	public String deposit(Model m, @RequestParam Integer cid, @RequestParam Double balance,
			@RequestParam Double deposit, RedirectAttributes ra) {
		Customers customer = cr.findById(cid).get();
		customer.setBalance(balance + deposit);
		cr.save(customer);
		ra.addFlashAttribute("message", "Deposit of $ " + deposit + " for Customer ID: " + cid + " is sucessful!");
		return "redirect:/customeraccounts";
	}
	
	@RequestMapping("/transfer")
	public String tranfer(Model m, @RequestParam Integer cidt, @RequestParam Double balancet,
			@RequestParam Double transfer, @RequestParam Integer transferid, RedirectAttributes ra) {
		Customers recipient = cr.getCustByCustid(transferid);
		Customers customer = cr.findById(cidt).get();
		if(recipient != null) {
		if (balancet >= transfer) {
		customer.setBalance(balancet - transfer);
		cr.save(customer);
//		Customers recipient = cr.findById(transferid).get();
		recipient.setBalance(recipient.getBalance() + transfer);
		cr.save(recipient);
		ra.addFlashAttribute("message", "Transfer of $ " + transfer + " to Customer ID: " + transferid + " is sucessful! The balance for Customer ID " + cidt + " is: $ " + customer.getBalance());
		return "redirect:/customeraccounts";
		} else {
			ra.addFlashAttribute("message", "Transfer of $ " + transfer + " to Customer ID: " + transferid + " is not sucessful. The balance remains as: $ " + customer.getBalance());
		return "redirect:/customeraccounts";
		}
		} else {
			ra.addFlashAttribute("message", "Customer ID: " + transferid + " does not exist");
			return "redirect:/customeraccounts";
		}
	}

	@RequestMapping("/withdraw")
	public String withdraw(Model m, @RequestParam Integer cid, @RequestParam Double balance,
			@RequestParam Double withdraw, RedirectAttributes ra) {		
		if (balance >= withdraw) {
			Customers customer = cr.findById(cid).get();
			customer.setBalance(balance - withdraw);
			cr.save(customer);
			ra.addFlashAttribute("message",
					"Withdrawal of $ " + withdraw + " for Customer ID: " + cid + " is sucessful!");
			return "redirect:/customeraccounts";
		} else {
			ra.addFlashAttribute("errormessage", "Withdrawal Unsuccessful. Balance Exceeded!");
			return "redirect:/customeraccounts";
		}
	}
	// End of Withdraw_Deposit Details
}
