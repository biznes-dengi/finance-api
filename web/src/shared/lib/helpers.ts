import {twMerge} from 'tailwind-merge';

export function cn(...classes: Array<unknown>) {
	return twMerge(classes.filter(Boolean).join(' '));
}

export function cloneDeep<T>(value: T): T {
	if (typeof value !== 'object' || value === null) {
		return value;
	}

	if (Array.isArray(value)) {
		const newArray: any[] = [];
		for (const item of value) {
			newArray.push(cloneDeep(item));
		}
		return newArray as T;
	}

	const newObj: Record<string, any> = {};
	for (const key in value) {
		if (Object.prototype.hasOwnProperty.call(value, key)) {
			newObj[key] = cloneDeep(value[key]);
		}
	}
	return newObj as T;
}

export function isEmpty(value: any): boolean {
	if (value == null) {
		return true;
	}

	if (Array.isArray(value) || typeof value === 'string' || value instanceof String) {
		return value.length === 0;
	}

	if (typeof value === 'object') {
		return Object.keys(value).length === 0;
	}

	return false;
}

export function isEqual(value: any, other: any): boolean {
	if (value === other) {
		return true;
	}

	if (typeof value !== 'object' || typeof other !== 'object' || value === null || other === null) {
		return false;
	}

	const keysA = Object.keys(value);
	const keysB = Object.keys(other);

	if (keysA.length !== keysB.length) {
		return false;
	}

	for (const key of keysA) {
		if (!isEqual(value[key], other[key])) {
			return false;
		}
	}

	return true;
}

export function isObject(value: unknown): value is object {
	return typeof value === 'object';
}

export function isArray(value: unknown[]): value is unknown[] {
	return Array.isArray(value);
}

export function isNull(value: unknown): value is null {
	return value === null;
}

export function isNumber(value: unknown): value is number {
	return typeof value === 'number';
}

export function isString(value: unknown): value is string {
	return typeof value === 'string';
}

export function isUndefined(value: unknown): value is undefined {
	return typeof value === 'undefined';
}

export function isBoolean(value: unknown): value is boolean {
	return typeof value === 'boolean';
}

export function get(object: any, path: string | string[], defaultValue: any = undefined): any {
	const keys = Array.isArray(path) ? path : path.split('.');

	let result = object;

	for (const key of keys) {
		if (result === null || result === undefined || !(key in result)) {
			return defaultValue;
		}
		result = result[key];
	}

	return result;
}
