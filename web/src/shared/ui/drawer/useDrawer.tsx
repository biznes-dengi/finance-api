import {ReactElement, ReactNode, useRef} from 'react';

import {Drawer, type DrawerProps as BottomDrawerProps} from './ui/Drawer.tsx';
import {SuccessDrawerContent} from './ui/SuccessDrawerContent.tsx';

type DrawerType = 'right' | 'bottom' | 'left';

type RightDrawerWrapperProps = {
	children: ReactNode;
};

type DrawerWrapperPropsMap = {right: RightDrawerWrapperProps; bottom: BottomDrawerProps; left: never};
type DrawerWrapperProps<T extends DrawerType> = T extends keyof DrawerWrapperPropsMap
	? DrawerWrapperPropsMap[T]
	: never;

export function useDrawer<T extends DrawerType>(drawerType: T = 'bottom' as T) {
	const openDrawerRef = useRef<() => void>(() => {});
	const closeDrawerRef = useRef<() => void>(() => {});

	const DrawerWrapper = {
		right: (props: RightDrawerWrapperProps) => (
			<Drawer {...{openDrawerRef, closeDrawerRef}} {...props} direction='right' withOverlay={false} isFullScreen />
		),
		bottom: (props: BottomDrawerProps) => <Drawer {...{openDrawerRef, closeDrawerRef}} {...props} />,
		left: () => null,
	}[drawerType] as (props: DrawerWrapperProps<T>) => ReactElement;

	return {
		Drawer: DrawerWrapper,
		SuccessDrawerContent,
		openDrawer: () => openDrawerRef.current(),
		closeDrawer: () => closeDrawerRef.current(),
	};
}
