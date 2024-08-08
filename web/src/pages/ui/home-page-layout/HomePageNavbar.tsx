import {cn} from '@shared/lib';
import {APP_PATH} from '@shared/config';
import {APP_ICON} from '@shared/ui';

const sidebarConfigs = [
	{
		label: 'Home',
		path: APP_PATH.root,
		icon: APP_ICON.HOME,
	},
	{
		label: 'Tracker',
		path: APP_PATH.root,
		icon: APP_ICON.TRACKER,
	},
	{
		label: 'Invest',
		path: APP_PATH.root,
		icon: APP_ICON.INVEST,
	},
	{
		label: 'Calculator',
		path: APP_PATH.root,
		icon: APP_ICON.CALCULATOR,
	},
];

/**
 * 1. navigate(path) = history.push(path) -- const navigate = useNavigate();
 *  2. for nav items hover box shadow (with space between active and hovered elements)
 */

export function HomePageNavbar() {
	return (
		<>
			<div className='mb-12 flex pl-4 text-2xl font-bold'>{APP_ICON.APP_LOGO}</div>
			<nav>
				{sidebarConfigs.map(({label, path, icon}, index) => (
					<div
						className={cn(
							'flex cursor-pointer rounded-2xl px-4 py-3 hover:bg-secondary-grey',
							index === 0 && 'bg-white',
						)}
						key={label + path}
						onClick={() => alert(label + ' module')}
					>
						<div className={cn('mr-4')}>{icon}</div>
						<div className={cn('font-medium text-primary-grey', index === 0 && 'text-primary-violet')}>{label}</div>
					</div>
				))}
			</nav>
		</>
	);
}
