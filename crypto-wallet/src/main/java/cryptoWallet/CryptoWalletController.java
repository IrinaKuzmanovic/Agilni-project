package cryptoWallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoWalletController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CryptoWalletRepository cryptoWalletRepository;
	
	@GetMapping("/crypto-wallet/email/{email}")
	public CryptoWallet getCryptoWalletByEmail(@PathVariable String email) {
		
		try {
		String port = environment.getProperty("local.server.port");
		CryptoWallet temp = cryptoWalletRepository.getCryptoWalletByEmail(email);
		
		return new CryptoWallet(temp.getId(), email, temp.getQuantityBTC(), temp.getQuantityETH(), 
				temp.getQuantityBNB(), port);
		}
		catch(Throwable e)
		{
			
			return null;
		}
	}

	@PutMapping("/crypto-wallet")
	public CryptoWallet updateCryptoWallet(@RequestBody CryptoWallet cryptoWallet) {
		
		if (!cryptoWalletRepository.existsById(cryptoWallet.getId())) {
			throw new RuntimeException("Crypto wallet doesn't exist!");
		}
		
		cryptoWalletRepository.save(cryptoWallet);
		String port = environment.getProperty("local.server.port");
		cryptoWallet.setEnvironment(port);
		return cryptoWallet;
	}
}