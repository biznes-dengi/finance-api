import {useNavigate} from 'react-router-dom';

import {TabConfig, Tabs} from '@shared/ui';
import {GoalManagement} from '@widgets/goal';

import {APP_PATH} from '@shared/config';

const tabConfigs = [{label: 'Goals', path: APP_PATH.root}, {label: 'Expenses'}];

export function GoalListPage() {
	const navigate = useNavigate();

	function handleTabChange(tabConfig: TabConfig) {
		tabConfig?.path && navigate(tabConfig.path);
	}

	return (
		<>
			<Tabs tabConfigs={tabConfigs} handleChange={handleTabChange} className='my-8 min-w-full' isOutsideCard />
			<GoalManagement />
		</>
	);
}
