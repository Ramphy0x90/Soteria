import { Entity } from "./entity";

export interface Credential {
  id: number | null;
  entityId: number | null | undefined;
  entity: Entity | null;
  userName: string;
  password: string;
}
