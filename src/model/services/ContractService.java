package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService service;
	
	
	public ContractService(OnlinePaymentService service) {
		this.service = service;
	}


	public void processContract(Contract contract, int months) {
		
		double basicQuota = contract.getTotalValue() / months;
		
		for(int i = 1; i <= months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i);
			
			double interest = service.interest(basicQuota, i);
			double fee = service.paymentFee(basicQuota + interest);
			double fullQuota = basicQuota + interest + fee;
			
			contract.getInstallments().add(new Installment(dueDate, fullQuota));
		}
		
	}

}
