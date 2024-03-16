import {ReactElement} from 'react';

import {BriefcaseIcon} from '@heroicons/react/24/outline';

import {cn} from '@shared/lib';

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import userIconPath from '@shared/assets/user.svg';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import pyramidIconPath from '@shared/assets/pyramid.svg';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import homeIconPath from '@shared/assets/home.svg';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import calculatorIconPath from '@shared/assets/calculator.svg';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import trackerIconPath from '@shared/assets/tracker.svg';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import investIconPath from '@shared/assets/invest.svg';

export const APP_ICON = {
	USER: 'USER',
	APP_LOGO: 'APP_LOGO',
	PORTFOLIO: 'PORTFOLIO',
	HOME: 'HOME',
	CALCULATOR: 'CALCULATOR',
	TRACKER: 'TRACKER',
	INVEST: 'INVEST',
} as const;

/** automate process
  1. Get values from APP_ICON and make iconMap
  2. Make config {iconPath, alt}
  3. Rest reuse
	*/

const iconMap = {
	[APP_ICON.USER]: ({className}: {className: string}) => (
		<img src={userIconPath} alt='user icon' className={cn(className)} />
	),
	[APP_ICON.APP_LOGO]: ({className}: {className: string}) => (
		<img src={pyramidIconPath} alt='pyramid icon' className={cn(className)} />
	),
	[APP_ICON.PORTFOLIO]: ({className}: {className: string}) => (
		<BriefcaseIcon className={cn(className, 'h-6 w-6 text-primary-grey')} />
	),
	[APP_ICON.HOME]: ({className}: {className: string}) => (
		<img src={homeIconPath} alt='home icon' className={cn(className)} />
	),
	[APP_ICON.CALCULATOR]: ({className}: {className: string}) => (
		<img src={calculatorIconPath} alt='home icon' className={cn(className)} />
	),
	[APP_ICON.INVEST]: ({className}: {className: string}) => (
		<img src={investIconPath} alt='home icon' className={cn(className)} />
	),
	[APP_ICON.TRACKER]: ({className}: {className: string}) => (
		<img src={trackerIconPath} alt='home icon' className={cn(className)} />
	),
} as any;

type IconProps = {
	name: string;
	className?: string;
};

export const Icon = ({name, className}: IconProps) => {
	const AppIcon = iconMap[name];

	return <AppIcon {...{className}} />;
};

type IconButtonProps = {
	children: ReactElement;
	handleClick: () => void;
};

export function IconButton({children, handleClick}: IconButtonProps) {
	return (
		<button
			className='flex h-10 w-10 cursor-pointer items-center justify-center rounded-2xl bg-secondary-violet'
			onClick={handleClick}
		>
			<div className='h-5 w-5'>{children}</div>
		</button>
	);
}