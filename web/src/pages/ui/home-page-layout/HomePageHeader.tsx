import {APP_ICON, Button, BUTTON_TYPE} from '@shared/ui';

export function HomePageHeader() {
	return (
		<header>
			<Button onClick={() => alert('click user icon')} type={BUTTON_TYPE.circle} icon={APP_ICON.USER} />
		</header>
	);
}
