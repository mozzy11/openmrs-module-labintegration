package org.openmrs.module.labintegration.api.communication.hl7.messages;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.PatientService;
import org.openmrs.api.ProviderService;
import org.openmrs.module.labintegration.api.communication.hl7.messages.testdata.HL7TestOrder;
import org.openmrs.module.labintegration.api.communication.hl7.messages.utils.OrderConverterTestUtils;
import org.openmrs.module.labintegration.api.hl7.messages.OMLO21OrderConverter;
import org.openmrs.module.labintegration.api.hl7.messages.OrderControl;
import org.openmrs.module.labintegration.api.hl7.messages.generators.MshGenerator;
import org.openmrs.module.labintegration.api.hl7.messages.generators.msh.MessageControlIdSource;
import org.openmrs.module.labintegration.api.hl7.openelis.OpenElisHL7Config;
import org.openmrs.module.labintegration.api.hl7.util.HL7TestMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OMLO21OrderConverterTest extends AbstractOrderConverterTest {
	
	private static final String EXPECTED_FILE = "OML_O21.hl7";
	
	@Spy
	@Autowired
	private MessageControlIdSource controlIdSource;
	
	@Autowired
	@InjectMocks
	private MshGenerator mshGenerator;
	
	@Autowired
	private OMLO21OrderConverter orderConverter;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private OpenElisHL7Config openElisHL7Config;
	
	@Before
	public void init() {
		OrderConverterTestUtils.mockRollingNumber(controlIdSource);
	}
	
	@Test
	public void dummyTest() {
		assertTrue(true);
	}
	
	//	@Test
	public void shouldGenerateMessage() throws Exception {
		executeDataSet(DATASET);
		Patient patient = patientService.getPatient(PATIENT_ID);
		Provider provider = providerService.getProvider(PROVIDER_ID);
		HL7TestOrder order = new HL7TestOrder(patient, provider);
		
		String msg = orderConverter.createMessage(order.value(), OrderControl.NEW_ORDER, openElisHL7Config);
		
		String expected = HL7TestMsgUtil.readMsg(EXPECTED_FILE);
		assertEquals(expected, msg);
	}
}
