package net.devstudy.resume.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import net.devstudy.resume.entity.Contact;
import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.entity.ProfileCollectionField;

public class UpdateLogicUtil {

	public static boolean profileCollectionChanged(List<? extends ProfileCollectionField> editedList, List<? extends ProfileCollectionField> currentList) {
		return !CollectionUtils.isEqualCollection(currentList, editedList);
	}

	public static boolean profileGeneralInfoChanged(Profile profile, Profile editedProfile) {
		if (!editedProfile.getFile().isEmpty())
			return true;
		if (!equalsFields(ProfileDataUtil.GENERAL_FIELDS, profile, editedProfile))
			return true;
		return false;
	}

	public static boolean profileAdditionalInfoChanged(Profile profile, Profile editedProfile) {
		if (!equalsFields(ProfileDataUtil.ADDITIONAL_FIELDS, profile, editedProfile)){
			return true;
		}
		return false;
	}

	private static boolean equalsFields(String[] fields, Object firstObject, Object secondObject) {
		try {
			for (String field : fields) {
				Object first = ObjectDataUtil.getObjectField(firstObject, field);
				Object second = ObjectDataUtil.getObjectField(secondObject, field);
				if (!((first == null && second == null) || first != null && first.equals(second))) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Can't check whether fields equal or not", e);
		}
	}

	public static boolean profileContactChanged(Contact currentContact, Contact editedContact) {
		return !currentContact.equals(editedContact);
	}
}