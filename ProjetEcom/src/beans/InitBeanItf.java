package beans;

import javax.ejb.Remote;

@Remote
public interface InitBeanItf {

	public void initBD();
	
}
