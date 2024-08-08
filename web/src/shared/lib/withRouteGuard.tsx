import {ReactNode} from 'react';

/**
 * High order component for DRY logic across routes
 * permissions
 * licenses
 * redirect
 * */

export function withRouteGuard(props: {page: ReactNode}) {
	return props.page;
}
