package net.bzk.flow.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import net.bzk.auth.model.CreateUpdateDate;
import net.bzk.auth.model.JsonPojoConverter;
import net.bzk.auth.service.CommService;
import net.bzk.flow.BzkFlowUtils;

@SuppressWarnings("serial")
@Data
@Entity
@EntityListeners(CommService.class)
@Table
public class Job implements Serializable, CreateUpdateDate {
	@Id
	@Column(nullable = false)
	private String uid;
	@Column(nullable = true, columnDefinition = "TEXT")
	@Convert(converter = FlowConvert.class)
	private Flow model;

	private Date updateAt;
	private Date createAt;

	public static Job gen(Flow f) {
		Job ans = new Job();
		ans.uid = f.getUid();
		return ans;
	}

	public static class FlowConvert extends JsonPojoConverter<Flow> {

		public static final ObjectMapper mapper = BzkFlowUtils.getFlowJsonMapper();

		@Override
		protected ObjectMapper mapper() {
			return mapper;
		}

		@Override
		public Class<Flow> getTClass() {
			return Flow.class;
		}
	}
}
