import AvatarFallBackInitials from "@/components/sidebar/nav-user";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { formatDate } from "@/lib/utils";
import useStore from "@/store/useStore";

function History() {
  const { activityHistory, currentUser } = useStore();

  return (
    <div className="max-h-[90lvh] overflow-y-scroll">
      <Table>
        <TableCaption>A list of the all action taken.</TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead></TableHead>
            <TableHead>Name</TableHead>
            <TableHead>Email</TableHead>
            <TableHead>Action</TableHead>
            <TableHead>Date</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {activityHistory
            .filter((activity) => activity.userId === currentUser?.email)
            .sort(
              (a, b) =>
                new Date(b.doneAt).getTime() - new Date(a.doneAt).getTime(),
            )
            .map((activity) => (
              <TableRow key={activity.id}>
                <TableCell className="font-medium">
                  <Avatar className="h-8 w-8 rounded-lg">
                    <AvatarImage
                      src={activity.users.avatar}
                      alt={activity.users.name}
                    />
                    <AvatarFallback className="rounded-lg">
                      <AvatarFallBackInitials
                        name={activity.users.name}
                        email={activity.users.email}
                      />
                    </AvatarFallback>
                  </Avatar>
                </TableCell>
                <TableCell className="font-medium">
                  {activity.users.name}
                </TableCell>
                <TableCell>{activity.users.email}</TableCell>
                <TableCell>{activity.description}</TableCell>
                <TableCell>{formatDate(activity.doneAt)}</TableCell>
              </TableRow>
            ))}
        </TableBody>
      </Table>
    </div>
  );
}
export default History;
