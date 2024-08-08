// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import userIconPath from '../../../assets/user.svg';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import pyramidIconPath from '../../../assets/pyramid.svg';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import homeIconPath from '../../../assets/home-primary-violet.svg';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import calculatorIconPath from '../../../assets/calculator.svg';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import trackerIconPath from '../../../assets/tracker.svg';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import investIconPath from '../../../assets/invest.svg';

import {
	ArrowDownIcon,
	ArrowLeftIcon,
	ArrowTrendingUpIcon,
	ArrowUpRightIcon,
	CalendarIcon,
	CameraIcon,
	CheckIcon,
	ChevronDownIcon,
	CloudArrowUpIcon,
	MagnifyingGlassIcon,
	PencilIcon,
	XMarkIcon,
} from '@heroicons/react/24/solid';
import {BriefcaseIcon, EllipsisVerticalIcon, FolderPlusIcon, PlusIcon} from '@heroicons/react/24/outline';

import {cn} from '@shared/lib';

export const APP_ICON = {
	USER: <img src={userIconPath} alt='user icon' />,
	APP_LOGO: <img src={pyramidIconPath} alt='pyramid icon' />,
	PORTFOLIO: <BriefcaseIcon className={cn('h-6 w-6 text-primary-grey')} />,
	HOME: <img src={homeIconPath} alt='home icon' />,
	CALCULATOR: <img src={calculatorIconPath} alt='calculator icon' />,
	INVEST: <img src={investIconPath} alt='invest icon' />,
	TRACKER: <img src={trackerIconPath} alt='tracket icon' />,
	createGoal: <FolderPlusIcon className={cn('h-6 w-6 text-primary-violet')} />,
	fund: <PlusIcon className={cn('h-6 w-6 text-primary-violet')} />,
	transfer: <ArrowUpRightIcon className={cn('h-6 w-6 text-primary-violet')} />,
	more: <EllipsisVerticalIcon className={cn('h-6 w-6 text-primary-violet')} />,
	backButton: <ArrowLeftIcon />,
	reset: <XMarkIcon className={cn('h-3 w-3')} />,
	search: <MagnifyingGlassIcon className={cn('h-4 w-4')} />,
	camera: <CameraIcon className={cn('h-5 w-5')} />,
	check: <CheckIcon />,
	uploadImage: <CloudArrowUpIcon />,
	transferTo: <ArrowDownIcon />,
	trendUp: <ArrowTrendingUpIcon />,
	edit: <PencilIcon />,
	calendar: <CalendarIcon />,
	selectArrow: <ChevronDownIcon />,
};
